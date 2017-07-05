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
package science.atlarge.graphalytics.openg.algorithms.sssp;

import science.atlarge.graphalytics.domain.algorithms.AlgorithmParameters;
import science.atlarge.graphalytics.domain.algorithms.SingleSourceShortestPathsParameters;
import science.atlarge.graphalytics.domain.benchmark.BenchmarkRun;
import science.atlarge.graphalytics.openg.OpengJob;
import science.atlarge.graphalytics.openg.OpengConfiguration;

/**
 * Single Source Shortest Paths job implementation for Openg. This class is responsible for formatting SSSP-specific
 * arguments to be passed to the platform executable, and does not include the implementation of the algorithm.
 *
 * @author Wing Lung Ngai
 */
public final class SingleSourceShortestPathsJob extends OpengJob {

	private final long sourceVertex;

	/**
	 * Creates a new BreadthFirstSearchJob object with all mandatory parameters specified.
	 *
	 * @param platformConfig the platform configuration.
	 * @param inputPath the path to the input graph.
	 */
	public SingleSourceShortestPathsJob(BenchmarkRun benchmarkRun,
										OpengConfiguration platformConfig,
										String inputPath, String outputPath) {
		super(benchmarkRun, platformConfig, inputPath, outputPath);

		AlgorithmParameters parameters = benchmarkRun.getAlgorithmParameters();
		this.sourceVertex = ((SingleSourceShortestPathsParameters)parameters).getSourceVertex();
	}

	@Override
	protected void appendAlgorithmParameters() {

		commandLine.addArgument("--algorithm");
		commandLine.addArgument("sssp");

		commandLine.addArgument("--source-vertex", false);
		commandLine.addArgument(String.valueOf(sourceVertex), false);
	}

}
