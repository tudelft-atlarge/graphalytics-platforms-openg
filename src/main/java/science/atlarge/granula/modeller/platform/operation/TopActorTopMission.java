/*
 * Copyright 2015 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package science.atlarge.granula.modeller.platform.operation;

import science.atlarge.granula.modeller.Type;
import science.atlarge.granula.modeller.rule.derivation.SimpleSummaryDerivation;
import science.atlarge.granula.modeller.rule.derivation.time.DurationDerivation;
import science.atlarge.granula.modeller.rule.derivation.time.FilialEndTimeDerivation;
import science.atlarge.granula.modeller.rule.derivation.time.FilialStartTimeDerivation;
import science.atlarge.granula.modeller.rule.linking.EmptyLinking;
import science.atlarge.granula.modeller.rule.visual.TableVisualization;

import java.util.ArrayList;

public class TopActorTopMission extends AbstractOperationModel {

    public TopActorTopMission() {
        super(Type.TopActor, Type.TopMission);
    }

    public void loadRules() {
        super.loadRules();

        addLinkingRule(new EmptyLinking());

        String summary = "?.";
        addInfoDerivation(new SimpleSummaryDerivation(11, summary));


        addInfoDerivation(new FilialStartTimeDerivation(6));
        addInfoDerivation(new FilialEndTimeDerivation(6));
        addInfoDerivation(new DurationDerivation(7));

        addVisualDerivation(new TableVisualization(1, "MainInfo",
                new ArrayList<String>() {{
                }}));

    }




}
