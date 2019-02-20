import spock.lang.*

class TestSpec extends Specification {

	@Unroll
	def "Test downstream build"() {

		given:
		println 'nothing'

		when:
		println 'something'

		then:
		println 'nothing #DOWNSTREAM_STEP'

		where:

		DOWNSTREAM                                                                      |DOWNSTREAM_STEP
		[[job: "../some-job/some-branch",parameters: []]] | true
		[
			[job: "../some-job/master", parameters: [[stuff: "blah"]], wait: false ]] | true
		[
			[job: "../some-job/dev"]] | true
		[
			[job: "../some-job/master"],
			[job: "../another-one/master"]] | true
		[
			[job: "     ", parameters: "param", wait: true]] | false
		[
			[job: "", wait: true]] | false
		null                                                                            | false
	}
}
