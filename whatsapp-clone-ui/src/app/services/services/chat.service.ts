/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ChatResponse } from '../models/chat-response';
import { createChat } from '../fn/chat/create-chat';
import { CreateChat$Params } from '../fn/chat/create-chat';
import { getChat } from '../fn/chat/get-chat';
import { GetChat$Params } from '../fn/chat/get-chat';
import { StringResponse } from '../models/string-response';

@Injectable({ providedIn: 'root' })
export class ChatService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getChat()` */
  static readonly GetChatPath = '/api/v1/chats';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getChat()` instead.
   *
   * This method doesn't expect any request body.
   */
  getChat$Response(params?: GetChat$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ChatResponse>>> {
    return getChat(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getChat$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getChat(params?: GetChat$Params, context?: HttpContext): Observable<Array<ChatResponse>> {
    return this.getChat$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ChatResponse>>): Array<ChatResponse> => r.body)
    );
  }

  /** Path part for operation `createChat()` */
  static readonly CreateChatPath = '/api/v1/chats';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createChat()` instead.
   *
   * This method doesn't expect any request body.
   */
  createChat$Response(params: CreateChat$Params, context?: HttpContext): Observable<StrictHttpResponse<StringResponse>> {
    return createChat(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createChat$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createChat(params: CreateChat$Params, context?: HttpContext): Observable<StringResponse> {
    return this.createChat$Response(params, context).pipe(
      map((r: StrictHttpResponse<StringResponse>): StringResponse => r.body)
    );
  }

}
