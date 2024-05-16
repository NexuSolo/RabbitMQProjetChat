import { UnknownAction, configureStore } from '@reduxjs/toolkit'
import { Socket, io } from 'socket.io-client';
// ...

export const store = configureStore({
  reducer: {
    socket: socketReducer,
  },
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch

function socketReducer(state: unknown, action: UnknownAction): Socket | null {
  return null;
}

