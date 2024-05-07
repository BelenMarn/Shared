package com.izertis.SimpleShared.secondaryAdapter.repository;

import com.izertis.SimpleShared.core.domain.Expense;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyExpenseListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.core.domain.exception.NegativeExpenseAmountException;
import com.izertis.SimpleShared.secondaryAdapter.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MysqlExpenseRepository implements ExpenseRepository {
    private final String SELECT_EXPENSES = "SELECT id_expense, id_friend, amount, " +
                                            "description, expense_date FROM expense";

    private final String INSERT_EXPENSE = "INSERT INTO expense(id_expense, id_friend, amount, " +
                                            "description, expense_date) VALUES (?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;
    private FriendRepository friendRepository;

    @Autowired
    public MysqlExpenseRepository(JdbcTemplate jdbcTemplate, FriendRepository friendRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendRepository =  friendRepository;
    }

    @Override
    public List<Expense> findExpenses() throws EmptyExpenseListException {
        RowMapper<ExpenseEntity>  mapper = new RowMapper<ExpenseEntity>() {
            @Override
            public ExpenseEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                ExpenseEntity expense = new ExpenseEntity();
                expense.setIdExpense(rs.getLong("id_expense"));
                expense.setIdFriend(rs.getLong("id_friend"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("description"));
                expense.setExpenseDate(rs.getString("expense_date"));
                return expense;
            }
        };
        List<ExpenseEntity> expenses = jdbcTemplate.query(SELECT_EXPENSES, mapper);

        if(!expenses.isEmpty()) {
            return toExpensesDomain(expenses);
        }else{
            throw new EmptyExpenseListException("No expenses found");
        }
    }

    @Override
    public void addExpense(Expense expense) throws NegativeExpenseAmountException {
        ExpenseEntity expenseEntity = toExpenseEntity(expense);

        if(expenseEntity.getAmount() <= 0){
            throw new NegativeExpenseAmountException("Amount must be positive");
        }else {
            jdbcTemplate.update(INSERT_EXPENSE, expenseEntity.getIdExpense(), expenseEntity.getIdFriend(),
                    expenseEntity.getAmount(), expenseEntity.getDescription(), expenseEntity.getExpenseDate());
        }
    }

    private List<Expense> toExpensesDomain(List<ExpenseEntity> expenses) {
        return expenses.stream()
                .map(expense -> {
                    Friend friend = null;
                    try {
                        friend = friendRepository.findFriendById(expense.getIdFriend());
                    } catch (FriendNotFoundException e) {
                        System.out.println("No friend found by id given.");
                    }
                    return new Expense(
                            expense.getIdExpense(),
                            friend,
                            expense.getAmount(),
                            expense.getDescription(),
                            expense.getExpenseDate()
                    );
                })
                .collect(Collectors.toList());
    }

    private ExpenseEntity toExpenseEntity(Expense expense) {
        return new ExpenseEntity(expense.getIdExpense(), expense.getFriend().getIdFriend(),
                expense.getAmount(), expense.getDescription(), expense.getExpenseDate());
    }

}
