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

package kieker.analysis.display;

/**
 * This is a display type used for plain text. It provides methods to modify and read the contained plain text and is thread safe.
 * 
 * @author Nils Christian Ehmke
 */
public class PlainText extends AbstractDisplay {

	/**
	 * The text stored within this object.
	 */
	private volatile String currText;

	/**
	 * Creates a new instance of this class with empty content.
	 */
	public PlainText() {
		this.currText = "";
	}

	/**
	 * This method sets the text of the object to a new value.
	 * 
	 * @param string
	 *            The new content of this object.
	 */
	public void setText(final String string) {
		this.currText = string;
	}

	/**
	 * Delivers the current text stored within this object.
	 * 
	 * @return The current value within this object.
	 */
	public String getText() {
		return this.currText;
	}

	@Override
	public String toString() {
		return this.getText();
	}
}
