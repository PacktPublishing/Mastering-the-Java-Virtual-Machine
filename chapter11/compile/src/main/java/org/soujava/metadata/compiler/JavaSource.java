package expert.os.metadata.compiler;

interface JavaSource<T> {

    String getSimpleName();

    String getName();

    String getJavaSource();

    Class<T> getType();
}