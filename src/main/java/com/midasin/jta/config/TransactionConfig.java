package com.midasin.jta.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // 클래스 Interface를 만들지 않아도 각 클래스에 Proxy가 걸리도록(CGLIB Proxy) ProxyTargetClass를 True로 설정한다
@EnableTransactionManagement
public class TransactionConfig {
    private static final String TX_METHOD_NAME = "*";
    private static final String TX_POINTCUT_EXPRESSION = "execution(* com.midasin..service.*Service.*(..))";

    @Bean
    @Primary
    public ChainedTransactionManager chainedTransactionManager(DataSourceTransactionManager masterTransactionManager,
                                                               DataSourceTransactionManager customerTransactionManager) {
        return new ChainedTransactionManager(masterTransactionManager, customerTransactionManager);
    }

    /**
     * 트랜잭션을 설정할 메소드명과 롤백룰을 정한다
     *
     * @author jsh1026
     * @date 2019-11-08 10:36
     */
    @Bean TransactionInterceptor chainedTransactionInterceptor(ChainedTransactionManager chainedTransactionManager) {
        return getTransactionInterceptor(chainedTransactionManager);
    }

    /**
     * Advisor 를 생성한다
     * PointCut 과 TransactionManager를 함께 설정해 서비스에서 실행된 SqlSession의 내용을 롤백되도록 한다
     *
     * @author jsh1026
     * @date 2019-05-22 09:01
     */
    @Bean
    public Advisor chainedTransactionAdvisor(
            @Autowired @Qualifier("chainedTransactionInterceptor") TransactionInterceptor chainedAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(TX_POINTCUT_EXPRESSION);
        // 포인트컷에 트랜젝션 인터셉터를 설정한다
        return new DefaultPointcutAdvisor(pointcut, chainedAdvice);
    }

    @Bean
    public DataSourceTransactionManager masterTransactionManager(
            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(masterDataSource);
        return transactionManager;
    }

    @Bean
    public DataSourceTransactionManager customerTransactionManager(
            @Autowired @Qualifier("customerDataSource") DataSource customerDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(customerDataSource);
        return transactionManager;
    }

    /**
     * transaction advice 를 생성한다
     *
     * 모든 메소드(*)에 RollbackRule(Exception) 발생시
     * <tx:advice id="txAdvice" transaction-manager="transactionManager">
     *     <tx:attributes>
     *         <tx:method name="*" rollback-for="Exception" timeout="3"/>
     *     </tx:attributes>
     * </tx:advice>
     * @param transactionManager
     */
    private TransactionInterceptor getTransactionInterceptor(PlatformTransactionManager transactionManager) {
        // 롤백 룰 선언 - 익셉션 발생시 롤백한다.
        RollbackRuleAttribute rollbackRuleAttribute = new RollbackRuleAttribute(Exception.class);
        // 모든 메소드명에 대해 위에서 생성한 롤백 룰을 가지는 트랜잭션 어트리뷰트
        RuleBasedTransactionAttribute ruleBasedTransactionAttribute = new RuleBasedTransactionAttribute();
        ruleBasedTransactionAttribute.setName(TX_METHOD_NAME);
        ruleBasedTransactionAttribute.setRollbackRules(Arrays.asList(rollbackRuleAttribute));
        // 트랜젝션 속성 설정
        MatchAlwaysTransactionAttributeSource matchAlwaysTransactionAttributeSource = new MatchAlwaysTransactionAttributeSource();
        matchAlwaysTransactionAttributeSource.setTransactionAttribute(ruleBasedTransactionAttribute);
        // 트랜잭션 인터셉터 생성 (서비스에서 익셉션 발생시 롤백하는 인터셉터)
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSource(matchAlwaysTransactionAttributeSource);

        return transactionInterceptor;
    }

}
