package com.example.demo.batch.configure

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.batch.support.DatabaseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

class CustomBatchConfigurer(
    @Autowired @Qualifier("repositoryDatasource") private val dataSource: DataSource,
    @Autowired @Qualifier("batchTransactionManager") private val transactionManager: PlatformTransactionManager
) : DefaultBatchConfigurer() {

    override fun createJobRepository(): JobRepository = JobRepositoryFactoryBean()
        .apply {
            setDatabaseType(DatabaseType.MYSQL.productName)
            setTablePrefix("FOO_")
            setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ")
            setDataSource(dataSource)
            afterPropertiesSet()
        }.`object`

    override fun getTransactionManager(): PlatformTransactionManager {
        return transactionManager
    }

    override fun createJobExplorer(): JobExplorer = JobExplorerFactoryBean()
        .apply {
            setDataSource(dataSource)
            setTablePrefix("FOO_")
            afterPropertiesSet()
        }.`object`

    override fun createJobLauncher(): JobLauncher = SimpleJobLauncher()
        .apply {
            setJobRepository(createJobRepository())
        }
}