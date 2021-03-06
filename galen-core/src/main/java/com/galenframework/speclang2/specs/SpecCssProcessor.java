/*******************************************************************************
* Copyright 2017 Ivan Shubin http://galenframework.com
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package com.galenframework.speclang2.specs;

import com.galenframework.parser.Expectations;
import com.galenframework.parser.SyntaxException;
import com.galenframework.specs.Spec;
import com.galenframework.specs.SpecCss;
import com.galenframework.specs.SpecText;
import com.galenframework.parser.StringCharReader;

public class SpecCssProcessor implements SpecProcessor {
    @Override
    public Spec process(StringCharReader reader, String contextPath) {

        String cssPropertyName = reader.readWord();
        String validationTypeString = reader.readWord();
        if (cssPropertyName.isEmpty()) {
            throw new SyntaxException("Missing css property name");
        }
        if (validationTypeString.isEmpty()) {
            throw new SyntaxException("Missing validation type (is, contains, starts, ends, matches)");
        }

        SpecText.Type validationType = SpecText.Type.fromString(validationTypeString);

        String expectedText = Expectations.doubleQuotedText().read(reader);


        if (reader.hasMoreNormalSymbols()) {
            throw new SyntaxException("Too many arguments for spec: " + reader.getTheRest().trim());
        }

        return new SpecCss(cssPropertyName, validationType, expectedText);
    }
}
