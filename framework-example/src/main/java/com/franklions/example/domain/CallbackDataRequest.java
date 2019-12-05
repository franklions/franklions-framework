package com.franklions.example.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-04-10
 * @since Jdk 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CallbackDataRequest {

    private String msgId;
    private Integer msgType;
    private String toAppId;
    private List<Object> content;
    private Long ts;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return "CallbackDataRequest{" +
                    "msgId='" + msgId + '\'' +
                    ", msgType=" + msgType +
                    ", toAppId='" + toAppId + '\'' +
                    ", content=" + objectMapper.writeValueAsString( content) +
                    ", ts=" + ts +
                    '}';
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "CallbackDataRequest{" +
                "msgId='" + msgId + '\'' +
                ", msgType=" + msgType +
                ", toAppId='" + toAppId + '\'' +
                ", content=null" +
                ", ts=" + ts +
                '}';
    }
}
