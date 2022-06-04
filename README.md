# SavedStateHandleStudy
Save and Restoring ViewModel data using SavedStateHandle


## main branch : with SavedStateHandle
Using SavedStateHandle, the ViewModel data can be restored when returning to the application, even if memory is released during task switching.

https://user-images.githubusercontent.com/750091/171999236-bcbbad6b-7164-4909-87eb-f6801884a4e9.mp4


## bad branch : without SavedStateHandle
Without the SavedStateHandle, the ViewModel data will be lost when returning to the application after memory has been released during other task use.

https://user-images.githubusercontent.com/750091/171999244-26ab6352-8711-43c6-9cb4-6b0809a6b035.mp4

