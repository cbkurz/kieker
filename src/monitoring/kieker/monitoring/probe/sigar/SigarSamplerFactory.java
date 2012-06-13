/***************************************************************************
 * Copyright 2012 by
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

package kieker.monitoring.probe.sigar;

import org.hyperic.sigar.Humidor;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxy;

import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.monitoring.probe.sigar.samplers.CPUsCombinedPercSampler;
import kieker.monitoring.probe.sigar.samplers.CPUsDetailedPercSampler;
import kieker.monitoring.probe.sigar.samplers.MemSwapUsageSampler;

/**
 * Provides factory methods for {@link kieker.monitoring.probe.sigar.samplers.AbstractSigarSampler}s.
 * 
 * @author Andre van Hoorn, Jan Waller
 */
public enum SigarSamplerFactory implements ISigarSamplerFactory { // Singleton pattern (Effective Java #3)

	INSTANCE;

	/**
	 * {@link SigarProxy} instance used to retrieve the data to be logged.
	 */
	private final SigarProxy sigar;

	/**
	 * Used by {@link #getInstance()} to construct the singleton instance.
	 */
	private SigarSamplerFactory() {
		final Log LOG = LogFactory.getLog(SigarSamplerFactory.class); // access to static logger not possible in constructor

		final Sigar mySigar = new Sigar();
		if (mySigar.getNativeLibrary() == null) {
			LOG.error("No Sigar native lib in java.library.path. See Sigar log for details (maybe only visible on Debug log-level).");
		}
		final Humidor humidor = new Humidor(mySigar);
		this.sigar = humidor.getSigar();
	}

	/**
	 * {@link SigarProxy} instance used by this {@link SigarSamplerFactory}.
	 * 
	 * @return the sigar
	 */
	public final SigarProxy getSigar() {
		return this.sigar;
	}

	public CPUsCombinedPercSampler createSensorCPUsCombinedPerc() {
		return new CPUsCombinedPercSampler(this.sigar);
	}

	public CPUsDetailedPercSampler createSensorCPUsDetailedPerc() {
		return new CPUsDetailedPercSampler(this.sigar);
	}

	public MemSwapUsageSampler createSensorMemSwapUsage() {
		return new MemSwapUsageSampler(this.sigar);
	}
}
