package com.wallet.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wallet.model.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WalletResponsePayload {

    public enum RESPONSE_STATUS {SUCCESS,FAILURE};

    private RESPONSE_STATUS status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private String responseDescription;

    public WalletResponsePayload(RESPONSE_STATUS status, Object data, String responseDescription) {
        this.status = status;
        this.data = data;
        this.responseDescription = responseDescription;
    }
}
