package com.foo.bar;
import java.util.Map;

import org.json.simple.JSONValue;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.Nimbus.Client;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.NimbusClient;
import backtype.storm.utils.Utils;

public class HelloStormThrift {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("RandomNumberGenerator-spout", new RandomNumberGenerator());
        builder.setBolt("word-spitter", new WordSpitterBolt())
                .shuffleGrouping("RandomNumberGenerator-spout");
        builder.setBolt("word-counter", new WordCounterBolt()).shuffleGrouping("word-spitter");
        //
        // Config conf = new Config();
        // conf.put(Config.NIMBUS_HOST, "localhost");
        // conf.setDebug(true);
        Map storm_conf = Utils.readStormConfig();
        // storm_conf.put("nimbus.host", "localhost");
        storm_conf.put("storm.zookeeper.retry.times", 7);

        Config config = new Config();
        // config.put("inputFile", args[0]);
        config.setDebug(true);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        storm_conf.putAll(config);
        Client client = NimbusClient.getConfiguredClient(storm_conf).getClient();
        String inputJar = "/Users/aeswara/Documents/workspace/StormThrift/target/StormThrift-0.0.1-SNAPSHOT.jar";
        NimbusClient nimbus = new NimbusClient(storm_conf, (String) storm_conf.get("nimbus.host"),
                (int) storm_conf.get("nimbus.thrift.port"));
        // upload topology jar to Cluster using StormSubmitter
        String uploadedJarLocation = StormSubmitter.submitJar(storm_conf, inputJar);
        try {
            String jsonConf = JSONValue.toJSONString(storm_conf);
            nimbus.getClient().submitTopology("testtopology", uploadedJarLocation, jsonConf,
                    builder.createTopology());
        } catch (AlreadyAliveException ae) {
            ae.printStackTrace();
        }
        Thread.sleep(60000);

    }

}