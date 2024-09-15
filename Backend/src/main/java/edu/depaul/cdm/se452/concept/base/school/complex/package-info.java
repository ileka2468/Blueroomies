/**
 * Define Different table relationship
 * <ul>
 * <li>One To One School to Address</li>
 * <li>One To Many University to School</li>
 * <li>Many to Many using Instructor To University</li>
 * </ul>
 * When building relationship, be sure to watch out for circular relationship.  @JsonIdentityInfo and @ToString.Exclude is key..
 */
package edu.depaul.cdm.se452.concept.base.school.complex;
