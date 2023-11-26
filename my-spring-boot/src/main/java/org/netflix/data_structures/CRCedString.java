package org.netflix.data_structures;

import org.json.JSONObject;

class CRCedString implements Comparable<CRCedString> {

    private static int idCounter = 1000000;
    private String s;
    private int len;
    private int crc;
    private Integer id;

    public CRCedString(String s, int len, int crc) {
        this.s = s;
        this.len = len;
        this.crc = crc;
        this.id = idCounter;
        ++idCounter;
    }

    @Override
    public int compareTo(CRCedString crCedString) {
        return Integer.compare(this.len, crCedString.len);
//        return this.s.compareTo(crCedString.s);
    }

    public String toString() {
        return String.format("#%d => (str: %s , len: %d , crc: %d )", this.id, this.s, this.len, this.crc);
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("len", len);
        object.put("crc", crc);
        object.put("s", s);
        return object;
    }

    public String getS() {
        return s;
    }

    public int getLen() {
        return len;
    }

    public int getCrc() {
        return crc;
    }

    public Integer getId() {
        return id;
    }
}
