package com.example.barterapp.utils;

public class URLs {
    public static final String base_url = "https://oranje-tech.com/demo/barter/api/";
    public static final String image_url = "http://oranje-tech.com/demo/barter/admin/images/users/";
    public static final String portfolio_images_url = "http://oranje-tech.com/demo/barter/admin/images/portfolio/";

    ////user urls
    public static final String signup_url = base_url + "signup";
    public static final String login_url = base_url + "login";
    public static final String change_pass_url = base_url + "updatePassword";
    public static final String logout_url = base_url + "logout";
    //// Jobs urls
    public static final String get_all_jobs_url = base_url + "getAllJob";
    public static final String add_new_job_url = base_url + "addJob";
    public static final String user_job_history_url = base_url + "getAllUserJobs";
    public static final String delete_user_job_url = base_url + "deleteUserJobs/";
    public static final String delete_user_offer_url = base_url + "deleteUserOffer/";
    public static final String search_job_url = base_url + "searchJob";
    public static final String create_offer_url = base_url + "createOffers";
    ////Tasks urls
    public static final String buy_sell_services_url = base_url + "getSellBuyService";
    ////Profile url
    public static final String current_user_url = base_url + "getCurrentUser";
    public static final String update_profile_url = base_url + "userUpdate";
    ////Chat urls
    public static final String get_all_inbox_messages_url = base_url + "getMessage";
    public static final String get_all_chat_messages_url = base_url + "getAllMessages/";
    public static final String add_messages = base_url + "addMessages";
    ////Term and conditions url
    public static final String terms_conditions_url = base_url + "getAllTerms";


}
