package edu.depaul.cdm.se452.rfa.agreements.payload;
import lombok.Data;

@Data
public class AgreementCreateRequest {
  private String content;
  private String receiver;
}
