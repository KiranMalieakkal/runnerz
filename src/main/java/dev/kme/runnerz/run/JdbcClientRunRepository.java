package dev.kme.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

   private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
       return jdbcClient.sql("select * from Run")
                .query(Run.class)
                .list();

    }

    Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location, version FROM Run where id=:id")
                .param("id",id)
                .query(Run.class)
                .optional();

    }
    public void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location)values(?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString()))
                .update();

        Assert.state(updated==1,"Failed to create run"+run.title());
    }

    void update(Run newrun,Integer id){
       var updated = jdbcClient.sql("update Run set title=?,started_on=?,completed_on=?,miles=?,location=? where id =?")
               .params(List.of(newrun.title(), newrun.startedOn(), newrun.completedOn(), newrun.miles(), newrun.location().toString(),id))
               .update();

        Assert.state(updated==1,"Failed to update run"+id);
    }
    void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM Run WHERE id= :id")
                .param("id",id)
                .update();
        Assert.state(updated==1,"Failed to delete run" +id);
    }
    public int count(){
        return jdbcClient.sql("select * from Run")
                .query()
                .listOfRows()
                .size();

    }
    // this method takes a list of run and
    public void saveAll(List<Run> runs){
        runs.stream().forEach(this::create);  //  runs.forEach(run -> create(run));
    }


    public List<Run> findByLocation(String location){
        return jdbcClient.sql("select * from Run where location = :location")
                .param("location",location)
                .query(Run.class)
                .list();

    }





































/*  private List<Run> runs = new ArrayList<>();
    @PostConstruct
    private void init(){
        runs.add(new Run(1,"run 1", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS),3,Location.OUTDOOR));
        runs.add(new Run(2,"run 2", LocalDateTime.now(), LocalDateTime.now().plus(2, ChronoUnit.HOURS),5,Location.OUTDOOR));
    }

    List<Run> findAll(){
        return runs;
    }

    Optional<Run> findById(Integer id){
        return runs.stream()
                .filter(run -> run.id()==id)
                .findFirst();

    }
    void create(Run run){
        runs.add(run);
    }

    void update(Run newrun,Integer id){
        Optional<Run> existingRun = findById(id);
        if(existingRun.isPresent()){
            runs.set(runs.indexOf(existingRun.get()),newrun);
        }
    }
    void delete(Integer id){
        runs.removeIf(run -> run.id()==id);
    }

 */
}
