package com.example.refining_gaushala_app.ui.slideshow;


public class GaushalaUpdateRequest {
    private Long gaushalaId;  // Changed to gaushalaId
    private String message;

    // Getters and setters
    public Long getGaushalaId() {
        return gaushalaId;
    }

    public void setGaushalaId(Long gaushalaId) {
        this.gaushalaId = gaushalaId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
        private double freshDungAmount;
        private double dryDungAmount;
        private double freshDungPrice;
        private double dryDungPrice;

        // Getters and setters
        public double getFreshDungAmount() {
            return freshDungAmount;
        }

        public void setFreshDungAmount(double freshDungAmount) {
            this.freshDungAmount = freshDungAmount;
        }

        public double getDryDungAmount() {
            return dryDungAmount;
        }

        public void setDryDungAmount(double dryDungAmount) {
            this.dryDungAmount = dryDungAmount;
        }

        public double getFreshDungPrice() {
            return freshDungPrice;
        }

        public void setFreshDungPrice(double freshDungPrice) {
            this.freshDungPrice = freshDungPrice;
        }

        public double getDryDungPrice() {
            return dryDungPrice;
        }

        public void setDryDungPrice(double dryDungPrice) {
            this.dryDungPrice = dryDungPrice;
        }
    }
