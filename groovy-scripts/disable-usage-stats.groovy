import jenkins.model.Jenkins

def j = Jenkins.instance
if(j.isUsageStatisticsCollected()){
    j.setNoUsageStatistics(true)
    j.save()
    println 'Disabled submitting usage stats to Jenkins project.'
}
