class TestSpec extends Specification {

  @Unroll
     def "Test downstream build"() {

         given:
         def triggerDownstreamJobsMock = Mock(Closure)
         helper.registerAllowedMethod('triggerDownstreamJobs', [List.class], triggerDownstreamJobsMock)

         when:
         executePipeline()

         then:
         assertJobStatusSuccess()

         and:
         helper.methodCallCount('triggerDownstreamJobs') == (DOWNSTREAM_STEP ? 1 : 0)

         where:
         
         DOWNSTREAM                                                                      |DOWNSTREAM_STEP
         [[job: "../some-job/some-branch",parameters: []]]                               | true
         [[job: "../some-job/master", parameters: [[stuff: "blah"]], wait: false ]]      | true
         [[job: "../some-job/dev"]]                                                      | true
         [[job: "../some-job/master"],[job: "../another-one/master"]]                    | true
         [[job: "     ", parameters: "param", wait: true]]                                    | false
         [[job: "", wait: true]]                                                         | false
         null                                                                            | false
     }
}
