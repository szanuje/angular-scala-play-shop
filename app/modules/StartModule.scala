package modules

import com.google.inject.AbstractModule
import utils.InitMongoDB

class StartModule extends AbstractModule {
  override def configure() = {
    bind(classOf[InitMongoDB]).asEagerSingleton()
  }
}