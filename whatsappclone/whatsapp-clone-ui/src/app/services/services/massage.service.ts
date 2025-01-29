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

import { getMessages } from '../fn/massage/get-messages';
import { GetMessages$Params } from '../fn/massage/get-messages';
import { MessageResponse } from '../models/message-response';
import { saveMessage } from '../fn/massage/save-message';
import { SaveMessage$Params } from '../fn/massage/save-message';
import { setMessagesToSeen } from '../fn/massage/set-messages-to-seen';
import { SetMessagesToSeen$Params } from '../fn/massage/set-messages-to-seen';
import { uploadMedia } from '../fn/massage/upload-media';
import { UploadMedia$Params } from '../fn/massage/upload-media';

@Injectable({ providedIn: 'root' })
export class MassageService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `saveMessage()` */
  static readonly SaveMessagePath = '/api/v1/messages';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveMessage()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveMessage$Response(params: SaveMessage$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return saveMessage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveMessage$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveMessage(params: SaveMessage$Params, context?: HttpContext): Observable<void> {
    return this.saveMessage$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `setMessagesToSeen()` */
  static readonly SetMessagesToSeenPath = '/api/v1/messages';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setMessagesToSeen()` instead.
   *
   * This method doesn't expect any request body.
   */
  setMessagesToSeen$Response(params: SetMessagesToSeen$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return setMessagesToSeen(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `setMessagesToSeen$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setMessagesToSeen(params: SetMessagesToSeen$Params, context?: HttpContext): Observable<void> {
    return this.setMessagesToSeen$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `uploadMedia()` */
  static readonly UploadMediaPath = '/api/v1/messages/upload-media';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadMedia()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadMedia$Response(params: UploadMedia$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return uploadMedia(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadMedia$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadMedia(params: UploadMedia$Params, context?: HttpContext): Observable<void> {
    return this.uploadMedia$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `getMessages()` */
  static readonly GetMessagesPath = '/api/v1/messages/chat/{chat-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMessages()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMessages$Response(params: GetMessages$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<MessageResponse>>> {
    return getMessages(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getMessages$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMessages(params: GetMessages$Params, context?: HttpContext): Observable<Array<MessageResponse>> {
    return this.getMessages$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<MessageResponse>>): Array<MessageResponse> => r.body)
    );
  }

}
