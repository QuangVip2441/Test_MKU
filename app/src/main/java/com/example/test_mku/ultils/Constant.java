package com.example.test_mku.ultils;

public class Constant {
    public static class Database {
        public static class Module {
            public static final String COLLECTION_MODULE = "module";
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String INTRODUCTION = "introduction";
            public static final String NUMBER_QUESTIONS = "numberQuestions";

        }

        public static class Question {
            public static final String COLLECTION_QUESTIONS = "questions";
            public static final String ID = "id";
            public static final String CONTENT = "content";
            public static final String CHOICES = "choices";
            public static final String CORRECT = "correct";

        }

        public static class Choice {
            public static final String ID = "id";
            public static final String CONTENT = "answer";

        }

        public class User {
            public static final String COLLECTION_USER = "user";
            public static final String ID = "id";
            public static final String EMAIL = "email";
            public static final String TOKEN = "token";
            public static final String USERNAME = "username";
            public static final String PHONE = "phone";
            public static final String PHOTO = "photo";
            public static final String STATUS = "status";
        }

    }
}
