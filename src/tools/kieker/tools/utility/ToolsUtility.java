/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
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

package kieker.tools.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;

/**
 * @author Nils Christian Ehmke
 * 
 * @since 1.10
 */
public class ToolsUtility {

	private static final Log LOG = LogFactory.getLog(ToolsUtility.class);

	private ToolsUtility() {}

	public static void loadVerboseLogger() {
		try {
			final LogManager logManager = LogManager.getLogManager();
			final InputStream configStream = ClassLoader.getSystemClassLoader().getResourceAsStream("logging.verbosely.properties");
			if (configStream != null) {
				logManager.readConfiguration(configStream);
			} else {
				LOG.warn("Could not load verbose logger");
			}
		} catch (final SecurityException ex) {
			LOG.warn("Could not load verbose logger", ex);
		} catch (final IOException ex) {
			LOG.warn("Could not load verbose logger", ex);
		}
	}

}
