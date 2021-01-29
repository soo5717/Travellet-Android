package com.example.travellet.feature.plan.distribute;

public class DistributeBudgetItem {
    String category;
    double budget;

    public DistributeBudgetItem(String category, double budget) {
        this.category = category;
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
