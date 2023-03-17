package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);


}
