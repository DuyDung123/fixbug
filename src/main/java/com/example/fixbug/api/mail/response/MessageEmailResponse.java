package com.example.fixbug.api.mail.response;

import java.util.List;

public class MessageEmailResponse {
    private String id;
    private String threadId;
    private List<String> labelIds;
    private String snippet;
    private Payload payload;
    private int sizeEstimate;
    private String historyId;
    private long internalDate;

    public String getId() {
        return id;
    }

    public String getThreadId() {
        return threadId;
    }

    public List<String> getLabelIds() {
        return labelIds;
    }

    public String getSnippet() {
        return snippet;
    }

    public Payload getPayload() {
        return payload;
    }

    public int getSizeEstimate() {
        return sizeEstimate;
    }

    public String getHistoryId() {
        return historyId;
    }

    public long getInternalDate() {
        return internalDate;
    }

    public static class Header{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Body{
        private int size;
        private String data;

        public int getSize() {
            return size;
        }

        public String getData() {
            return data;
        }
    }

    public static class Part{
        private String partId;
        private String mimeType;
        private String filename;
        private List<Header> headers;
        private Body body;

        public String getPartId() {
            return partId;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getFilename() {
            return filename;
        }

        public List<Header> getHeaders() {
            return headers;
        }

        public Body getBody() {
            return body;
        }
    }

    public static class Payload{
        private String partId;
        private String mimeType;
        private String filename;
        private List<Header> headers;
        private Body body;
        private List<Part> parts;

        public String getPartId() {
            return partId;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getFilename() {
            return filename;
        }

        public List<Header> getHeaders() {
            return headers;
        }

        public Body getBody() {
            return body;
        }

        public List<Part> getParts() {
            return parts;
        }
    }
}
