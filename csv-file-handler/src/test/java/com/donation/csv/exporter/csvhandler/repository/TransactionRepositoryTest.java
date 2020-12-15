package com.donation.csv.exporter.csvhandler.repository;

import com.donation.csv.exporter.csvhandler.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldBeAbleToPersist() {
        Transaction transaction = Transaction.builder()
                .bankAccountNumber("1100205607")
                .bankName("Huntington National Bank")
                .bankRoutingNumber("not known")
                .billingAccountNumber("not known")
                .glInvoice("not known")
                .ftiInfo("not known")
                .cardNumber("not known")
                .cardType("Visa")
                .ccAuthCode("tst138")
                .payor("not known")
                .terminalName("not known")
                .timeStamp("9/1/2020 16:51")
                .corpName("UPMC Benefit Mgmt Services")
                .entityName("Benefit Management Services Retiree")
                .glAccountNumber(278251)
                .glBu("HPBMS")
                .glDepartment("36857")
                .glDescription("not known")
                .externalAppSiteConfigurationId("not known")
                .kioskName("not known")
                .billingSystem("e8f5ea6a-5ed3-411d-9809-ad4b89f83e78")
                .dos("not known")
                .invoice("not known")
                .productOrProvider("not known")
                .patAccount("Non-Patient")
                .amount(new BigDecimal("2.22"))
                .bankAccount("*****6789")
                .bankAccountName("not known")
                .bankAccountType("Savings Account")
                .cashier("not known")
                .checkNumber("not known")
                .maker("LINDA ZANG")
                .patientEmailAddress("mattav@upmc.edu")
                .patientFirstName("LINDA")
                .patientLastName("ZANG")
                .patientMiddleName("kr")
                .routingNumber("36076150")
                .tenderType("Savings Account")
                .transactionDate("9/1/2020 16:51")
                .transactionDateTime("9/1/2020 16:51")
                .transactionId(5363891)
                .transactionType("SALE")
                .typeOfPhoneOrPremises("not known")
                .contractId(0)
                .originalTransactionDate("not known")
                .originalTransactionId(null)
                .refundApprovalFlag("not known")
                .refundApprovalStatus("not known")
                .refundApprover("not known")
                .refundDate("not known")
                .refundReason("not known")
                .refundType("not known")
                .refundBy("not known")
                .userLoginId("vmatta")
                .achBankData("not known")
                .activeFlag("not known")
                .depositSlipLocationNumber("not known")
                .siteName("not known")
                .siteTypeId("not known")
                .workSiteId("not known")
                .workSiteName("Benefit Management Svcs Retiree")
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);


    }
}