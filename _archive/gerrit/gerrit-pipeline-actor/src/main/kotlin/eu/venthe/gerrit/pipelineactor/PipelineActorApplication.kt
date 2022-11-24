package eu.venthe.gerrit.pipelineactor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PipelineActorApplication

fun main(args: Array<String>) {
	runApplication<PipelineActorApplication>(*args)
}
