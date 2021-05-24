import com.google.inject.AbstractModule
//import services.ProductService

class StartModule extends AbstractModule {
  override def configure() = {
    bind(classOf[InitMongoDB]).asEagerSingleton()
    // bind(classOf[ProductService]).asEagerSingleton()
  }
}