package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(1, 100.00);
        double ExpectedFees = 5.0 + 0.1;
        double ExpectedBalance = this.bankAccount.getBalance() - ExpectedFees;
        
        double RealFees = this.bankAccount.getBalance();
        this.bankAccount.chargeManagementFees(1);
        double RealBalance = this.bankAccount.getBalance();
        RealFees-=this.bankAccount.getBalance();

        System.out.println("Expected fees: " + ExpectedFees);
        System.out.println("Real fees: " + RealFees);
        assertEquals(ExpectedBalance, RealBalance, 0.001);
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            this.bankAccount.withdraw(1, -1);
            fail("Il prelievo negativo doveva essere bloccato, ma è stato permesso!");
        } catch(IllegalArgumentException a) {
            System.out.println("Failed withdrawing negative amount"+a.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            this.bankAccount.withdraw(1, this.bankAccount.getBalance()+1);
            fail("Il prelievo negativo doveva essere bloccato, ma è stato permesso!");
        } catch(IllegalArgumentException a) {
            System.out.println("Failed withdrawing more money than balance"+a.getMessage());
        }
    }
}
