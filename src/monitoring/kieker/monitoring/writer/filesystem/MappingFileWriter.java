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

package kieker.monitoring.writer.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import kieker.common.record.IMonitoringRecord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Andre van Hoorn, Jan Waller
 */
public final class MappingFileWriter {
	private static final Log log = LogFactory.getLog(MappingFileWriter.class);

	private final File mappingFile;
	private final AtomicInteger nextId = new AtomicInteger(1); // first ID is 1
	private final Map<Class<? extends IMonitoringRecord>, Integer> class2idMap = new ConcurrentHashMap<Class<? extends IMonitoringRecord>, Integer>();

	public MappingFileWriter(final String mappingFileFn) throws IOException {
		this.mappingFile = new File(mappingFileFn);
		// TODO: Check return value of createNewFile and act accordingly
		// See ticket http://samoa.informatik.uni-kiel.de/kieker/trac/ticket/227
		this.mappingFile.createNewFile();
	}

	public final int idForRecordTypeClass(final Class<? extends IMonitoringRecord> clazz) {
		final Integer idObj = this.class2idMap.get(clazz);
		if (idObj == null) {
			final int id = nextId.getAndIncrement();
			this.class2idMap.put(clazz, id);
			this.writeMapping(id, clazz.getName());
			return id;
		}
		return idObj;
	}

	private final void writeMapping(final int id, final String className) {
		MappingFileWriter.log.info("Registered monitoring record type with id '" + id + "':" + className);
		try {
			final PrintWriter pw = new PrintWriter(new FileOutputStream(this.mappingFile, true));
			pw.println("$" + id + "=" + className);
			pw.close();
		} catch (final Exception ex) {
			MappingFileWriter.log.fatal("Failed to register record type", ex);
		}
	}
}
