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

package science.atlarge.granula.modeller.platform;

import science.atlarge.granula.modeller.Type;
import science.atlarge.granula.modeller.job.Job;
import science.atlarge.granula.modeller.job.Overview;
import science.atlarge.granula.modeller.platform.info.BasicInfo;
import science.atlarge.granula.modeller.platform.info.Source;
import science.atlarge.granula.modeller.platform.operation.Operation;
import science.atlarge.granula.modeller.rule.derivation.DerivationRule;
import science.atlarge.granula.modeller.rule.extraction.OpenGExtractionRule;
import science.atlarge.granula.modeller.rule.filling.UniqueOperationFilling;
import science.atlarge.granula.modeller.platform.operation.*;
import science.atlarge.granula.modeller.platform.operation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Openg extends PlatformModel {

    public Openg() {
        super();
        addOperationModel(new TopActorTopMission());
        addOperationModel(new OpenGJob());
        addOperationModel(new LoadGraph());
        addOperationModel(new OffloadGraph());
        addOperationModel(new ProcessGraph());
    }

    public void loadRules() {

        addFillingRule(new UniqueOperationFilling(2, Type.TopActor, Type.TopMission));

        addInfoDerivation(new JobNameDerivationRule(4));
        addInfoDerivation(new JobInfoRule(20));
        addExtraction(new OpenGExtractionRule(1));

    }


    protected class JobInfoRule extends DerivationRule {

        public JobInfoRule(int level) {
            super(level);
        }

        @Override
        public boolean execute() {

            Platform platform = (Platform) entity;
            platform.setName("A OpenG job");
            platform.setType("OpenG");

            Job job = platform.getJob();
            Overview overview = job.getOverview();

            overview.setDescription("OpenG: tbd");

            Operation jobOper = platform.findOperation(Type.OpenG, Type.Job);
            jobOper.parentId = null;
            platform.addRoot(jobOper.getUuid());

            try {
                Operation processGraph = platform.findOperation(Type.OpenG, Type.ProcessGraph);
                long processingTime = Long.parseLong(processGraph.getInfo("Duration").getValue());

                Operation loadGraph = platform.findOperation(Type.OpenG, Type.LoadGraph);
                long loadTime = Long.parseLong(loadGraph.getInfo("Duration").getValue());

                Operation topOperation = platform.findOperation(Type.OpenG, Type.Job);
                long totalTime = Long.parseLong(topOperation.getInfo("Duration").getValue());

                long otherTime = totalTime - loadTime - processingTime;

                Map<String, Long> breakDown = new HashMap<>();
                breakDown.put("ProcessingTime", processingTime);
                breakDown.put("IOTime", loadTime);
                breakDown.put("Overhead", otherTime);
                overview.setBreakDown(breakDown);
            } catch (Exception e) {
                System.out.println(String.format("JobInfoRule encounter %s exception when calculating breakdown.", e.toString()));
            }



            return true;


        }
    }


    protected class JobNameDerivationRule extends DerivationRule {

        public JobNameDerivationRule(int level) {
            super(level);
        }

        @Override
        public boolean execute() {

            Platform platform = (Platform) entity;
//
//
//            BasicInfo jobNameInfo = new BasicInfo("JobName");
//            jobNameInfo.addInfo("unspecified", new ArrayList<Source>());
//            job.addInfo(jobNameInfo);
//
//            String jobName = null;
//            List<Operation> operations = job.findOperations(Type.MRApp, Type.MRJob);
//            for (Operation operation : operations) {
//                jobName = operation.getInfo("JobName").getValue();
//            }
//            if(jobName == null) {
//                throw new IllegalStateException();
//            }

            platform.setName("A OpenG job");
            platform.setType("OpenG");

            return true;

        }
    }


    public BasicInfo basicInfo(String key, String value) {
        List<Source> sources = new ArrayList<>();
        BasicInfo info = new BasicInfo(key);
        info.setDescription("No description was set.");
        info.addInfo(value, sources);
        return info;
    }
}
