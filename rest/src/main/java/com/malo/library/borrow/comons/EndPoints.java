package com.malo.library.borrow.comons;

public class EndPoints {
    public final static String APP_ROOT="/library";
    public static final String BORROW_VERSION = ApiVersion.V1;
    public static final String BORROW_PATH = Scope.BORROW;

    public static final String RETURN_VERSION = ApiVersion.V1;
    public static final String RETURN_PATH = Scope.RETURN;

    public static class PathVariables {
        public static final String ID = "id";
    }

    public static class Scope {
        public static final String BORROW = "/borrow";
        public static final String RETURN = "/return";
    }

    public static class ApiVersion {
        public static final String V1 = "/v1";
    }
}
