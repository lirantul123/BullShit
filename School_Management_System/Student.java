package School_Management_System;

/**
 * InnerStudent
 */
 interface InnerStudent {
    public  class Student {
        
        private int studentClass;
        private int id;
        private Grade[] grades;

        public Student(int studentClass, int id,  Grade[] grades) {
            this.studentClass = studentClass;
            this.id = id;
            for (int i = 0; i < grades.length; i++) {
                this.grades[i] = grades[i];

            }            
        }
        
        public int getStudentClass() {
            return studentClass;
        }
        public int getId() {
            return id;
        }
        public Grade[] getGrades() {
            return grades;
        }

        //public abstract void study();

    }
    
}
