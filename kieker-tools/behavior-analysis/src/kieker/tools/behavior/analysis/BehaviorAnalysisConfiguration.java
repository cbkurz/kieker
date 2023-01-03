/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package kieker.tools.behavior.analysis;

import com.google.common.graph.MutableNetwork;

import kieker.analysis.behavior.ModelGenerationCompositeStage;
import kieker.analysis.behavior.acceptance.matcher.GenericEntryCallAcceptanceMatcher;
import kieker.analysis.behavior.acceptance.matcher.IEntryCallAcceptanceMatcher;
import kieker.analysis.behavior.clustering.ClusteringCompositeStage;
import kieker.analysis.behavior.clustering.IParameterWeighting;
import kieker.analysis.behavior.clustering.UserBehaviorCostFunction;
import kieker.analysis.behavior.model.UserBehaviorEdge;
import kieker.analysis.generic.graph.INode;
import kieker.analysis.generic.graph.clustering.ClusterMedoidSink;
import kieker.analysis.generic.graph.clustering.Clustering;
import kieker.analysis.generic.graph.clustering.ClusteringFileSink;
import kieker.analysis.generic.graph.clustering.GraphEditDistance;
import kieker.analysis.generic.graph.clustering.NaiveMediodGenerator;
import kieker.analysis.generic.source.time.TimeReaderStage;
import kieker.common.exception.ConfigurationException;
import kieker.tools.common.ParameterEvaluationUtils;
import kieker.tools.source.LogsReaderCompositeStage;

import teetime.framework.Configuration;
import teetime.stage.basic.distributor.Distributor;
import teetime.stage.basic.distributor.strategy.CopyByReferenceStrategy;

/**
 *
 * @author Lars Jürgensen
 * @since 2.0.0
 */
public class BehaviorAnalysisConfiguration extends Configuration {

	public BehaviorAnalysisConfiguration(final BehaviorAnalysisSettings settings, final kieker.common.configuration.Configuration configuration)
			throws ConfigurationException {

		final double nodeInsertCost = configuration.getDoubleProperty(ConfigurationKeys.NODE_INSERTION_COST, 10);

		final double edgeInsertCost = configuration.getDoubleProperty(ConfigurationKeys.EDGE_INSERTION_COST, 5);

		final double eventGroupInsertCost = configuration
				.getDoubleProperty(ConfigurationKeys.EVENT_GROUP_INSERTION_COST, 4);

		final IParameterWeighting weighting = ParameterEvaluationUtils.createFromConfiguration(IParameterWeighting.class, configuration,
				ConfigurationKeys.PARAMETER_WEIGHTING, "missing parameter weighting function.");

		final UserBehaviorCostFunction costFunction = new UserBehaviorCostFunction(nodeInsertCost, edgeInsertCost, eventGroupInsertCost, weighting);

		final LogsReaderCompositeStage reader = new LogsReaderCompositeStage(configuration);

		final IEntryCallAcceptanceMatcher entryCallAcceptanceMatcher = new GenericEntryCallAcceptanceMatcher(settings.getClassSignatureAcceptancePatterns(),
				settings.getOperationSignatureAcceptancePatterns(),
				settings.getAcceptanceMatcherMode());
		final ModelGenerationCompositeStage modelGeneration = new ModelGenerationCompositeStage(entryCallAcceptanceMatcher,
				settings.getTraceSignatureProcessor(), settings.getUserSessionTimeout());
		final ClusteringCompositeStage<INode, UserBehaviorEdge> clustering = new ClusteringCompositeStage<>(settings.getClusteringDistance(),
				settings.getMinPts(), settings.getMaxAmount(), costFunction);
		final Distributor<Clustering<MutableNetwork<INode, UserBehaviorEdge>>> distributor = new Distributor<>(new CopyByReferenceStrategy());

		// Replace this for file based operation with an end of execution trigger.
		final TimeReaderStage timerStage = new TimeReaderStage(1l, 1l);

		this.connectPorts(reader.getOutputPort(), modelGeneration.getInputPort());

		this.connectPorts(modelGeneration.getModelOutputPort(), clustering.getModelInputPort());
		this.connectPorts(timerStage.getOutputPort(), clustering.getTimerInputPort());
		this.connectPorts(clustering.getOutputPort(), distributor.getInputPort());

		if (settings.getClusterOutputPath() != null) {
			final ClusteringFileSink<INode, UserBehaviorEdge> sink = new ClusteringFileSink<>(settings.getClusterOutputPath());
			this.connectPorts(distributor.getNewOutputPort(), sink.getInputPort());
		}

		if (settings.getMedoidOutputPath() != null) {
			final GraphEditDistance<INode, UserBehaviorEdge> graphEditDistance = new GraphEditDistance<>(costFunction);

			final NaiveMediodGenerator<INode, UserBehaviorEdge> medoid = new NaiveMediodGenerator<>(graphEditDistance);
			final ClusterMedoidSink<INode, UserBehaviorEdge> sink = new ClusterMedoidSink<>(settings.getMedoidOutputPath());

			this.connectPorts(distributor.getNewOutputPort(), medoid.getInputPort());
			this.connectPorts(medoid.getOutputPort(), sink.getInputPort());
		}
	}
}
