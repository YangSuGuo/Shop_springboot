package edu.cdtu.entity.user;

import lombok.Data;

@Data
public class CodeKey {
    private String key;
    private String captchaBase64;
}
