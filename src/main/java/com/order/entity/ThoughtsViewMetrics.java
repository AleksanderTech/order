package com.order.entity;

public class ThoughtsViewMetrics {

    public int leftPosition;
    public int topPosition;
    public int rightPosition;
    public int bottomPosition;
    public int width;
    public int height;

    public ThoughtsViewMetrics() {
    }

    public ThoughtsViewMetrics(int leftPosition, int topPosition, int rightPosition, int bottomPosition, int width, int height) {
        this.leftPosition = leftPosition;
        this.topPosition = topPosition;
        this.rightPosition = rightPosition;
        this.bottomPosition = bottomPosition;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "ThoughtsViewMetrics{" +
                "leftPosition=" + leftPosition +
                ", topPosition=" + topPosition +
                ", rightPosition=" + rightPosition +
                ", bottomPosition=" + bottomPosition +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public static ThoughtsViewMetrics.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int leftPosition;
        private int topPosition;
        private int rightPosition;
        private int bottomPosition;
        private int width;
        private int height;

        public Builder leftPosition(int leftPosition) {
            this.leftPosition = leftPosition;
            return this;
        }

        public Builder topPosition(int topPosition) {
            this.topPosition = topPosition;
            return this;
        }

        public Builder rightPosition(int rightPosition) {
            this.rightPosition = rightPosition;
            return this;
        }

        public Builder bottomPosition(int bottomPosition) {
            this.bottomPosition = bottomPosition;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "leftPosition=" + leftPosition +
                    ", topPosition=" + topPosition +
                    ", rightPosition=" + rightPosition +
                    ", bottomPosition=" + bottomPosition +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

        public ThoughtsViewMetrics build() {
            return new ThoughtsViewMetrics(this.leftPosition, this.topPosition, this.rightPosition, this.bottomPosition, this.width, this.height);
        }
    }
}
