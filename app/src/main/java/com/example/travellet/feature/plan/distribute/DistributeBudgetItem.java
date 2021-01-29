package com.example.travellet.feature.plan.distribute;

public class DistributeBudgetItem {
    int category, count;
    double budget;

    public DistributeBudgetItem(int category, int count, double budget) {
        this.category = category;
        this.count = count;
        this.budget = budget;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
