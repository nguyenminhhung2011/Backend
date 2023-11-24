package com.FitnessApp.Utils.Constants;

public class Constants {
    //API
    public static final String ACCESS_TOKEN= "access_token";

    public static final String REFRESH_TOKEN= "refresh_token";

    public static final String BEARER= "Bearer ";

    public static final String AUTHORIZATION= "Authorization";

    public static final int IS_BAN= 2;

    public static final int IS_ACTIVE = 1;

    public static final int IS_NOT_ACTIVE = 0;

    public static final int IS_EDITED = 0;

    public static final int IS_NOT_EDITED = 1;

    public static final String ROLE_PATIENT= "PATIENT";

    public static final String ROLE_DOCTOR= "DOCTOR";

    public static final String ROLE_SYSTEM_ADMIN = "SYSTEM ADMIN";

    public static final String ROLE_BUSINESS_ADMIN = "BUSINESS ADMIN";
    //Otp
    public static final String OTP_EXPIRE_MESSAGE = "Mã Otp đã hết hiệu lực.";

    public static final String OTP_INCORRECT = "Mã OTP không hợp lệ.";

    public static final String OTP_VALID = "Mã OTP đã được xác thực.";

    public static final int HIDDEN_FEEDBACK= 0;

    public static final int SHOW_FEEDBACK= 1;

    public static final int GENDER_MALE= 0;

    public static final int GENDER_FEMALE= 1;

    public static final int GENDER_OTHER= 2;

    public static final int SYSTEM_CANCEL_STATUS= 11;

    public static final int IS_NOT_CIRCULATE= 1;

    public static final int IS_CIRCULATE= 0;

    public static final int IS_SHARE_MED= 0;

    public static final int IS_NOT_SHARE_MED= 1;

    //Firebase
    public static final String COLLECTION_NOTIFICATION= "notifications";

    public static final String COLLECTION_NOTIFICATION_MESSAGE= "message";

    //Payment

    public static final String PAYMENT_SUCCESS_MESSAGE= "Giao dich thanh cong";

    public static final String PAYMENT_FAILED_MESSAGE= "Giao dich khong thanh cong";

    //Mesage
    public static final String UPCOMING_APPOINTMENT_MESSAGE= "Sắp đến ca khám của bạn vui lòng chú ý để vào khám đúng giờ.";

    public static final String ON_TIME_APPOINTMENT_MESSAGE= "Đã đến giờ khám vui lòng vào phòng khám.";

    public static final String NEW_APPOINTMENT_MESSAGE= "Bạn có 1 lịch khám mới.";
}
