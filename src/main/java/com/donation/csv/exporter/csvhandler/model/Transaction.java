package com.donation.csv.exporter.csvhandler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@ToString
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @JsonProperty("Bank Account Number")
    public String bankAccountNumber;
    @JsonProperty("Bank Name")
    public String bankName;
    @JsonProperty("Bank Routing Number")
    public String bankRoutingNumber;
    @JsonProperty("Billing Account #")
    public String billingAccountNumber;
    @JsonProperty("GL Invoice")
    public String glInvoice;
    @JsonProperty("FTI Info")
    public String ftiInfo;
    @JsonProperty("Card No")
    public String cardNumber;
    @JsonProperty("Card Type")
    public String cardType;
    @JsonProperty("CC Auth Code")
    public String ccAuthCode;
    @JsonProperty("Payor")
    public String payor;
    @JsonProperty("Terminal Name")
    public String terminalName;
    @JsonProperty("Time Stamp")
    public String timeStamp;
    @JsonProperty("Corp Name")
    public String corpName;
    @JsonProperty("Entity Name")
    public String entityName;
    @JsonProperty("GL Acct")
    public String glAccountNumber;
    @JsonProperty("GL BU")
    public String glBu;
    @JsonProperty("GL Dept")
    public String glDepartment;
    @JsonProperty("GL Descript")
    public String glDescription;
    @JsonProperty("External App Site Configuration ID")
    public String externalAppSiteConfigurationId;
    @JsonProperty("Kiosk Name")
    public String kioskName;
    @JsonProperty("Billing System")
    public String billingSystem;
    @JsonProperty("DOS (date of Service)")
    public String dos;
    @JsonProperty("Invoice")
    public String invoice;
    @JsonProperty("Product/Provider")
    public String productOrProvider;
    @JsonProperty("Pat Acct")
    public String patAccount;
    @JsonProperty("Amount")
    public BigDecimal amount;
    @JsonProperty("Bank Account")
    public String bankAccount;
    @JsonProperty("Bank Account Name")
    public String bankAccountName;
    @JsonProperty("Bank Account Type")
    public String bankAccountType;
    @JsonProperty("Cashier")
    public String cashier;
    @JsonProperty("Check Number")
    public String checkNumber;
    @JsonProperty("Maker")
    public String maker;
    @JsonProperty("Patient Email Address")
    private String patientEmailAddress;
    @JsonProperty("Patient First Name")
    private String patientFirstName;
    @JsonProperty("Patient Last Name")
    private String patientLastName;
    @JsonProperty("Patient Middle Name")
    private String patientMiddleName;
    @JsonProperty("Routing Number")
    private String routingNumber;
    @JsonProperty("Tender Type")
    private String tenderType;
    @JsonProperty("Txn Date")
    private String transactionDate;
    @JsonProperty("Txn Date Time")
    private String transactionDateTime;
    @JsonProperty("Txn ID")
    private String transactionDateId;
    @JsonProperty("Txn Type")
    private String transactionType;


}
