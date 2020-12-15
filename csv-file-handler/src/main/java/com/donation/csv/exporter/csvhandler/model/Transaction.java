package com.donation.csv.exporter.csvhandler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction_store")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @Column(name = "bank_account_number")
    @JsonProperty("Bank Account Number")
    public String bankAccountNumber;
    @Column(name = "bank_name")
    @JsonProperty("Bank Name")
    public String bankName;
    @Column(name = "bank_routing_number")
    @JsonProperty("Bank Routing Number")
    public String bankRoutingNumber;
    @Column(name = "billing_account_number")
    @JsonProperty("Billing Account #")
    public String billingAccountNumber;
    @Column(name = "gl_invoice")
    @JsonProperty("GL Invoice")
    public String glInvoice;
    @Column(name = "fti_info")
    @JsonProperty("FTI Info")
    public String ftiInfo;
    @Column(name = "card_number")
    @JsonProperty("Card No")
    public String cardNumber;
    @Column(name = "card_type")
    @JsonProperty("Card Type")
    public String cardType;
    @Column(name = "cc_auth_code")
    @JsonProperty("CC Auth Code")
    public String ccAuthCode;
    @Column(name = "payor")
    @JsonProperty("Payor")
    public String payor;
    @Column(name = "terminal_name")
    @JsonProperty("Terminal Name")
    public String terminalName;
    @Column(name = "time_stamp")
    @JsonProperty("Time Stamp")
    public String timeStamp;
    @Column(name = "corp_name")
    @JsonProperty("Corp Name")
    public String corpName;
    @Column(name = "entity_name")
    @JsonProperty("Entity Name")
    public String entityName;
    @Column(name = "gl_account_number")
    @JsonProperty("GL Acct")
    public int glAccountNumber;
    @Column(name = "gl_bu")
    @JsonProperty("GL BU")
    public String glBu;
    @Column(name = "gl_department")
    @JsonProperty("GL Dept")
    public String glDepartment;
    @Column(name = "gl_description")
    @JsonProperty("GL Descript")
    public String glDescription;
    @Column(name = "external_app_site_configuration_id")
    @JsonProperty("External App Site Configuration ID")
    public String externalAppSiteConfigurationId;
    @Column(name = "kiosk_name")
    @JsonProperty("Kiosk Name")
    public String kioskName;
    @Column(name = "billing_system")
    @JsonProperty("Billing System")
    public String billingSystem;
    @Column(name = "dos")
    @JsonProperty("DOS (date of Service)")
    public String dos;
    @Column(name = "invoice")
    @JsonProperty("Invoice")
    public String invoice;
    @Column(name = "product_provider")
    @JsonProperty("Product/Provider")
    public String productOrProvider;
    @Column(name = "pat_account")
    @JsonProperty("Pat Acct")
    public String patAccount;
    @Column(name = "amount")
    @JsonProperty("Amount")
    public BigDecimal amount;
    @Column(name = "bank_account")
    @JsonProperty("Bank Account")
    public String bankAccount;
    @Column(name = "bank_account_name")
    @JsonProperty("Bank Account Name")
    public String bankAccountName;
    @Column(name = "bank_account_type")
    @JsonProperty("Bank Account Type")
    public String bankAccountType;
    @Column(name = "cashier")
    @JsonProperty("Cashier")
    public String cashier;
    @Column(name = "check_number")
    @JsonProperty("Check Number")
    public String checkNumber;
    @Column(name = "maker")
    @JsonProperty("Maker")
    public String maker;
    @Column(name = "patient_email_address")
    @JsonProperty("Patient Email Address")
    private String patientEmailAddress;
    @Column(name = "patient_first_name")
    @JsonProperty("Patient First Name")
    private String patientFirstName;
    @Column(name = "patient_last_name")
    @JsonProperty("Patient Last Name")
    private String patientLastName;
    @Column(name = "patient_middle_name")
    @JsonProperty("Patient Middle Name")
    private String patientMiddleName;
    @Column(name = "routing_number")
    @JsonProperty("Routing Number")
    private String routingNumber;
    @Column(name = "tender_type")
    @JsonProperty("Tender Type")
    private String tenderType;
    @Column(name = "transaction_date")
    @JsonProperty("Txn Date")
    private String transactionDate;
    @Column(name = "transaction_date_time")
    @JsonProperty("Txn Date Time")
    private String transactionDateTime;
    @Id
    @Column(name = "transaction_id")
    @JsonProperty("Txn ID")
    private int transactionId;
    @Column(name = "transaction_type")
    @JsonProperty("Txn Type")
    private String transactionType;
    @Column(name = "type_of_phone_or_premises")
    @JsonProperty("Type (On Premise / Phone)")
    private String typeOfPhoneOrPremises;
    @Column(name = "contract_id")
    @JsonProperty("Contract ID")
    private int contractId;
    @Column(name = "original_transaction_date")
    @JsonProperty("Orig Txn Date")
    private String originalTransactionDate;
    @Column(name = "original_transaction_id")
    @JsonProperty("Orig Txn ID")
    private String originalTransactionId;
    @Column(name = "refund_approval_flag")
    @JsonProperty("Refund Approval Flag")
    private String refundApprovalFlag;
    @Column(name = "refund_approval_status")
    @JsonProperty("Refund Approval Status")
    private String refundApprovalStatus;
    @Column(name = "refund_approver")
    @JsonProperty("Refund Approver")
    private String refundApprover;
    @Column(name = "refund_date")
    @JsonProperty("Refund Date")
    private String refundDate;
    @Column(name = "refund_reason")
    @JsonProperty("Refund Reason")
    private String refundReason;
    @Column(name = "refund_type")
    @JsonProperty("Refund Type")
    private String refundType;
    @Column(name = "refund_by")
    @JsonProperty("Refunded By")
    private String refundBy;
    @Column(name = "user_login_id")
    @JsonProperty("User login ID")
    private String userLoginId;
    @Column(name = "ach_bank_data")
    @JsonProperty("ACH Bank Data")
    private String achBankData;
    @Column(name = "active_flag")
    @JsonProperty("Active Flag")
    private String activeFlag;
    @Column(name = "deposit_slip_location_number")
    @JsonProperty("Deposit Slip Location Number")
    private String depositSlipLocationNumber;
    @Column(name = "site_name")
    @JsonProperty("Site Name")
    private String siteName;
    @Column(name = "site_type_id")
    @JsonProperty("Site Type ID")
    private String siteTypeId;
    @Column(name = "worksite_id")
    @JsonProperty("Worksite ID")
    private String workSiteId;
    @Column(name = "worksite_name")
    @JsonProperty("Worksite Name")
    private String workSiteName;
}
