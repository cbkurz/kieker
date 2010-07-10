package kieker.analysis.plugin.configuration;

import java.util.ArrayList;
import java.util.Collection;
import kieker.analysis.datamodel.IAnalysisEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * ==================LICENCE=========================
 * Copyright 2006-2010 Kieker Project
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
 */
/**
 *
 * @author Andre van Hoorn
 */
public abstract class AbstractInputPort<T extends IAnalysisEvent> implements IInputPort<T> {

    private static final Log log = LogFactory.getLog(AbstractInputPort.class);
    private final String description;

    private AbstractInputPort() {
        this.description = null;
    }

    public AbstractInputPort(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
