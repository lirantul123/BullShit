package School_Management_System;

class Grade {
    private String subject;
    private double subGrade;

    public Grade(String subject, double subGrade) {
        this.subject = subject;
        this.subGrade = subGrade;
    }

    public double getSubGrade() {
        return subGrade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubGrade(double subGrade) {
        this.subGrade = subGrade;
    }

    @Override
    public String toString() {
        return subject + ": " + subGrade;
    }
}

