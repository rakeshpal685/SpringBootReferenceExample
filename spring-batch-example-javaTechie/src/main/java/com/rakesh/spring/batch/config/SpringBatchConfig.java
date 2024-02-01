package com.rakesh.spring.batch.config;

import com.rakesh.spring.batch.entity.Customer;
import com.rakesh.spring.batch.partition.ColumnRangePartition;
import com.rakesh.spring.batch.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;

    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("csvReader");//any name
        itemReader.setLinesToSkip(1);//skip the first header line of the csv
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    @Bean
    public ColumnRangePartition columnRangePartition(){
        return new ColumnRangePartition();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
//        Here i am telling to parse the customer to the repository
        writer.setRepository(customerRepository);
//        I am telling to call this save method of the repository,it is similar to customerRepository.save() method
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository).<Customer, Customer>chunk(10, platformTransactionManager).reader(reader()).processor(processor()).writer(writer()).taskExecutor(taskExecutor()).build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importCustomers", jobRepository).start(step1())
//                .next(step2())
                .build();
    }

    //Here I am defining my own task executor where I can define any number of threads to work partially
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
// Here I am asking 10 threads to work in async to parse the data, in this case the data in the DB will be randomized
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

//        Telling where to separate the columns in thr csv
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

//        This is used to map the csv file to the customer object
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }
}
