package com.foo.bar;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
public class RandomNumberGenerator implements IRichSpout {
    private SpoutOutputCollector collector;
    private boolean completed = false;
    private TopologyContext context;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.context = context;
        this.collector = collector;

    }

    @Override
    public void nextTuple() {
        if (completed) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String SourceString = RandomStringUtils.random(30);
        this.collector.emit(new Values(SourceString), SourceString);

    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }

    @Override
    public void close() {
    }
    public boolean isDistributed() {
        return false;
    }
    @Override
    public void activate() {
    }
    @Override
    public void deactivate() {
    }
    @Override
    public void ack(Object msgId) {
    }
    @Override
    public void fail(Object msgId) {
    }
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}