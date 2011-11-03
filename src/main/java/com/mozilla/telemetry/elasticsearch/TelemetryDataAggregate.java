/*
 * Copyright 2011 Mozilla Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mozilla.telemetry.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class TelemetryDataAggregate {

    public static class Info {
        private String appName;
        private String appVersion;
        @JsonProperty("OS") private String OS;
        @JsonProperty("appBuildID") private String appBuildId;
        @JsonProperty("platformBuildID") private String platformBuildId;
        private String arch;
        private String version;
        
        public String getAppName() {
            return appName;
        }
        public void setAppName(String appName) {
            this.appName = appName;
        }
        public String getAppVersion() {
            return appVersion;
        }
        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }
        public String getOS() {
            return OS;
        }
        public void setOS(String os) {
            OS = os;
        }
        public String getAppBuildId() {
            return appBuildId;
        }
        public void setAppBuildId(String appBuildId) {
            this.appBuildId = appBuildId;
        }
        public String getPlatformBuildId() {
            return platformBuildId;
        }
        public void setPlatformBuildId(String platformBuildId) {
            this.platformBuildId = platformBuildId;
        }
        public String getArch() {
            return arch;
        }
        public void setArch(String arch) {
            this.arch = arch;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
    }
    
    public static class Histogram {
        
        private List<int[]> values = new ArrayList<int[]>();
        private int count;
        private long sum;
        
        public List<int[]> getValues() {
            return values;
        }

        public void addValue(int[] value) {
            this.values.add(value);
        }
        
        public void setValues(List<int[]> values) {
            this.values = values;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount(int count) {
            this.count += count;
        }
        
        public void setCount(int count) {
            this.count = count;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }
    }
    
    private String date;
    private Info info;
    private Map<String,Histogram> histograms = new HashMap<String,Histogram>();
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public void setHistograms(Map<String, Histogram> histograms) {
        this.histograms = histograms;
    }
    
    public Map<String, Histogram> getHistograms() {
        return histograms;
    }

    public void addOrPutHistogramValue(String key, String histValueKey, Integer histValue) {
        Histogram hist = null;
        if (histograms.containsKey(key)) {
            hist = histograms.get(key);
        } else {
            hist = new Histogram();
        }

        hist.addValue(new int[] { Integer.parseInt(histValueKey), histValue });
        histograms.put(key, hist);
    }
    
    public void incrementHistogramCount(String key, int count) {
        Histogram hist = null;
        if (histograms.containsKey(key)) {
            hist = histograms.get(key);
        } else {
            hist = new Histogram();
        }
        
        hist.incrementCount(count);
        histograms.put(key, hist);
    }

    public void setHistogramSum(String key, long sum) {
        Histogram hist = null;
        if (histograms.containsKey(key)) {
            hist = histograms.get(key);
        } else {
            hist = new Histogram();
        }
        
        hist.setSum(sum);
    }
}