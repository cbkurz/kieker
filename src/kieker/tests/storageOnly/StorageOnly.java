package kieker.tests.storageOnly;
import kieker.tpmon.TpmonController;
import kieker.tpmon.annotations.TpmonInternal;

/**
 *
 * This is a small test for the part of Tpmon that
 * stores monitoring data. Tpmon's monitoring API 
 * is manually invoked to collect monitoring data. 
 * Therefore, the instrumentation and logic in the
 * monitoring points (the aspects) of Tpmon is not
 * used. 
 *
 * The main purpose of this test is to isolate configuration and 
 * installation problems and to get Tpmon running.
 *
 * Just compile and start it with the KiekerTpmonCTW library
 * in the classpath (no javaagent required). 
 *
 * If in tpmon.properties file system storage (store in database = 
 * false) is selected, a new file (tpmon*.dat) with monitoring data
 * should be created in the folder specified in tpmon.properties
 * (default: /tmp).
 *
 * ==================LICENCE=========================
 * Copyright 2006-2008 Matthias Rohr and the Kieker Project
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
 * ==================================================
 * 
 * @author Matthias Rohr
 *
 * History:
 * 2008-05-05 small refactoring for first release
 * 2008-03-30 initial version
 */

public class StorageOnly { 
    @TpmonInternal   
    public static void main(String args[]) {
        try {
            System.out.println("Starting test by adding 10000 monitoring events");
            for (int i = 0; i < 10000; i++) {
                TpmonController.getInstance().insertMonitoringDataNow(i%2 + "component", i%4 + "method", "sessionid", "requestid", 123123L, 123124L,i,i);
            }
            System.out.println("Sleeping for 8 seconds");
            Thread.sleep(8000);
            System.out.println("10000 more monitoring points");
            for (int i = 0; i < 10000; i++) {
                TpmonController.getInstance().insertMonitoringDataNow(i%2 + "component", i%4 + "method", "sessionid", "requestid", 123123L, 123124L,i+10000,i);
            }
            System.out.println("Calling system.exit(0)");
            System.exit(0);
        } catch (InterruptedException ex) {
            System.out.println("Exception:"+ex);
			ex.printStackTrace();
		}        
    }
}
