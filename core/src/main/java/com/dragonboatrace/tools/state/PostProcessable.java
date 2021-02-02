package com.dragonboatrace.tools.state;

// https://blog.simplypatrick.com/tils/2016/2016-03-02-post-processing-gson-deserialization/
// https://medium.com/mobile-app-development-publication/post-processing-on-gson-deserialization-26ce5790137d

/**
 * Common interface for objects that are post processable by gson.
 *
 * Only the interface is public.
 */
public interface PostProcessable {
  void postProcess();
}
