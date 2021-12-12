package io.transaction.spring.tx;

/**
 * @author: lichunxia
 * @create: 11/21/21 11:20 AM
 */
public class ProgrammicTransaction {

    public static void main(String[] args) {
        // // 定义事务
        // DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // def.setName("SomeTxName");
        // def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // // txManager，事务管理器
        // // 通过事务管理器开启一个事务
        // TransactionStatus status = txManager.getTransaction(def);
        // try {
        //     // 完成自己的业务逻辑
        // } catch (Exception ex) {
        //     // 出现异常，进行回滚
        //     txManager.rollback(status);
        //     throw ex;
        // }
        // // 正常执行完成，提交事务
        // txManager.commit(status);
    }

}
