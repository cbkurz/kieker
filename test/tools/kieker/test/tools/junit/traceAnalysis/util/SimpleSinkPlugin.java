/***************************************************************************
 * Copyright 2011 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
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

package kieker.test.tools.junit.traceAnalysis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kieker.analysis.plugin.AbstractAnalysisPlugin;
import kieker.analysis.plugin.port.InputPort;
import kieker.analysis.repository.AbstractRepository;
import kieker.common.configuration.Configuration;

/**
 * 
 * @author Nils Ehmke
 * 
 */
public class SimpleSinkPlugin extends AbstractAnalysisPlugin {

	public static final String INPUT_PORT_NAME = "input";
	private final List<Object> list = new ArrayList<Object>();

	public SimpleSinkPlugin() {
		super(new Configuration(), new HashMap<String, AbstractRepository>());
	}

	@InputPort(name = SimpleSinkPlugin.INPUT_PORT_NAME)
	public void input(final Object data) {
		this.list.add(data);
	}

	public void clear() {
		this.list.clear();
	}

	public List<Object> getList() {
		return this.list;
	}

	@Override
	protected Configuration getDefaultConfiguration() {
		return null;
	}

	@Override
	public Configuration getCurrentConfiguration() {
		return null;
	}

	@Override
	public boolean execute() {
		return true;
	}

	@Override
	public void terminate(final boolean error) {}

	@Override
	public Map<String, AbstractRepository> getCurrentRepositories() {
		return new HashMap<String, AbstractRepository>();
	}
}
