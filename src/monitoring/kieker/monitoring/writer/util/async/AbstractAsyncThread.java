package kieker.monitoring.writer.util.async;

import java.util.concurrent.BlockingQueue;

import kieker.common.record.DummyMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.monitoring.core.MonitoringController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * ==================LICENCE=========================
 * Copyright 2006-2011 Kieker Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==================================================
 */

/**
 * @author Andre van Hoorn, Jan Waller
 */
public abstract class AbstractAsyncThread extends Thread {
	private static final Log log = LogFactory.getLog(AbstractAsyncThread.class);

	private final static IMonitoringRecord END_OF_MONITORING_MARKER = new DummyMonitoringRecord();
	private volatile boolean finished = false;
	private final BlockingQueue<IMonitoringRecord> writeQueue;

	public AbstractAsyncThread(final BlockingQueue<IMonitoringRecord> writeQueue) {
		this.writeQueue = writeQueue;
	}
	
	public final void initShutdown() {
		try {
			this.writeQueue.put(END_OF_MONITORING_MARKER);
		} catch (final InterruptedException ex) {
			AbstractAsyncThread.log.error("Error while trying to stop writer thread", ex);
		}
	}

	public final boolean isFinished() {
		return this.finished;
	}

	@Override
	public final void run() {
		AbstractAsyncThread.log.debug(this.getClass().getName() + " running");
		// making it a local variable for faster access
		final BlockingQueue<IMonitoringRecord> writeQueue = this.writeQueue;
		try {
			while (!this.finished) {
				try {
					IMonitoringRecord monitoringRecord = writeQueue.take();
					if (monitoringRecord == END_OF_MONITORING_MARKER) {
						AbstractAsyncThread.log.debug("Terminating writer thread, " + writeQueue.size() + " entries remaining");
						monitoringRecord = writeQueue.poll();
						while (monitoringRecord != null) {
							if (monitoringRecord != END_OF_MONITORING_MARKER) {
								this.consume(monitoringRecord);
							}
							monitoringRecord = writeQueue.poll();
						}
						this.finished = true;
						this.writeQueue.put(END_OF_MONITORING_MARKER);
						break;
					} else {
						this.consume(monitoringRecord);
					}
				} catch (final InterruptedException ex) {
					// would be another method to finish the execution
					// but normally we should be able to continue
				}
			}
			AbstractAsyncThread.log.debug("Writer thread finished");
		} catch (final Exception ex) {
			// e.g. Interrupted Exception or IOException
			AbstractAsyncThread.log.error("Writer thread will halt", ex);
			AbstractAsyncThread.log.error("Will terminate monitoring!");
			this.finished = true;
			MonitoringController.getInstance().terminateMonitoring();
		} finally {
			this.finished = true;
		}
	}

	protected abstract void consume(final IMonitoringRecord monitoringRecord) throws Exception;
}
