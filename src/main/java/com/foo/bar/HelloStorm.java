package com.foo.bar;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

public class HelloStorm {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.put("inputFile", args[0]);
        config.setDebug(true);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("RandomNumberGenerator-spout", new RandomNumberGenerator());
        builder.setBolt("word-spitter", new WordSpitterBolt())
                .shuffleGrouping("RandomNumberGenerator-spout");
        builder.setBolt("word-counter", new WordCounterBolt()).shuffleGrouping("word-spitter");

        // LocalCluster cluster = new LocalCluster();
        StormSubmitter.submitTopology("HelloStorm", config, builder.createTopology());
        Thread.sleep(20000);

        // cluster.shutdown();
    }

}